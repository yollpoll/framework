package com.yollpoll.annotationprocessor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
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
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Created by spq on 2021/3/31
 */
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
        mMessage.printMessage(Diagnostic.Kind.NOTE, "onMessage processing...");
        //得到所有的注解
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(OnMessage.class);
        Set<? extends Element> elements2 = roundEnvironment.getElementsAnnotatedWith(MethodReference.class);


        for (Element element : elements) {
            if (null == proxy) {
                proxy = new MRCreatorProxy(mElementUtils, element, mMessage);
            }
            proxy.add(element);
        }

        for (Element element : elements2) {
            if (null == proxy) {
                proxy = new MRCreatorProxy(mElementUtils, element, mMessage);
            }
            proxy.add(element);
        }

        mMessage.printMessage(Diagnostic.Kind.NOTE, "num: "+proxy.list.size());


        JavaFile javaFile = JavaFile.builder(proxy.getPackageName(), proxy.generateJavaCode()).build();
        try {
            //　生成文件
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
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
}
