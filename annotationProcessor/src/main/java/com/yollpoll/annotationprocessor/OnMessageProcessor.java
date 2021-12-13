package com.yollpoll.annotationprocessor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.sun.tools.javac.processing.JavacFiler;
import com.yollpoll.annotation.annotation.MethodReference;
import com.yollpoll.annotation.annotation.OnMessage;
import com.yollpoll.annotationprocessor.proxy.MRCreatorProxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class OnMessageProcessor extends AbstractProcessor {
    private MRCreatorProxy proxy;
    private Messager mMessage;
    private Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessage = processingEnv.getMessager();
        mElementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mMessage.printMessage(Diagnostic.Kind.NOTE, "OnMessage/MethodReference。。。");
        //得到所有的注解
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(OnMessage.class);
        Set<? extends Element> elements2 = roundEnvironment.getElementsAnnotatedWith(MethodReference.class);


        for (Element element : elements) {
            if (null == proxy) {
                proxy = new MRCreatorProxy(mElementUtils, element, mMessage);
            }
            if (element.getKind() == ElementKind.METHOD) {
                proxy.add(element);
            } else {
                error(element, "Only method can be annotated with @OnMessage",
                        OnMessage.class.getSimpleName());
                return true; // 退出处理
            }
        }

        for (Element element : elements2) {
            if (null == proxy) {
                proxy = new MRCreatorProxy(mElementUtils, element, mMessage);
            }
            if (element.getKind() == ElementKind.METHOD) {
                proxy.add(element);
            } else {
                error(element, "Only method can be annotated with @MethodReference",
                        MethodReference.class.getSimpleName());
                return true; // 退出处理
            }
        }

        mMessage.printMessage(Diagnostic.Kind.NOTE, "num: " + proxy.list.size());
        mMessage.printMessage(Diagnostic.Kind.NOTE,"packageName: " + proxy.getPackageName());


        if (proxy.list.size() == 0) {
            return true;//如果没有收集到注解，提前退出
        }
        JavaFile javaFile = JavaFile.builder(proxy.getPackageName(), proxy.generateJavaCode()).build();
        try {
            //　生成文件
            javaFile.writeTo(processingEnv.getFiler());
            //processor回调会多轮启动，第二轮启动扫描的是第一轮生成出来的java文件
            //所有一次生成完以后清空已有数据
            //当前这个场景下第二轮不会出现新的文件，所以直接清空然后退出就可以
            proxy.list.clear();
        } catch (IOException e) {
            e.printStackTrace();
            mMessage.printMessage(Diagnostic.Kind.NOTE, e.getMessage());
        }
        mMessage.printMessage(Diagnostic.Kind.NOTE, "process finish ...");
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(OnMessage.class.getCanonicalName());
        supportTypes.add(MethodReference.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void error(Element e, String msg, Object... args) {
        mMessage.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }
}
