//package com.yollpoll.annotationprocessor;
//
//import com.google.auto.service.AutoService;
//import com.squareup.javapoet.JavaFile;
//import com.yollpoll.annotation.annotation.BindView;
//import com.yollpoll.annotationprocessor.proxy.ClassCreatorProxy;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.LinkedHashSet;
//import java.util.Map;
//import java.util.Set;
//
//import javax.annotation.processing.AbstractProcessor;
//import javax.annotation.processing.Messager;
//import javax.annotation.processing.ProcessingEnvironment;
//import javax.annotation.processing.Processor;
//import javax.annotation.processing.RoundEnvironment;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.Element;
//import javax.lang.model.element.TypeElement;
//import javax.lang.model.element.VariableElement;
//import javax.lang.model.util.Elements;
//import javax.tools.Diagnostic;
//
///**
// * Created by spq on 2021/3/31
// */
//@AutoService(Processor.class)
//public class BindViewProcessor extends AbstractProcessor {
//    private Messager mMessager;
//    private Elements mElementUtils;
//    private Map<String, ClassCreatorProxy> mProxyMap = new HashMap<>();
//
//    @Override
//    public synchronized void init(ProcessingEnvironment processingEnv) {
//        super.init(processingEnv);
//        mMessager = processingEnv.getMessager();
//        mElementUtils = processingEnv.getElementUtils();
//    }
//
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        HashSet<String> supportTypes = new LinkedHashSet<>();
//        supportTypes.add(BindView.class.getCanonicalName());
//        return supportTypes;
//    }
//
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.latestSupported();
//    }
//
//    @Override
//    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        mMessager.printMessage(Diagnostic.Kind.NOTE, "processing...");
//        mProxyMap.clear();
//        //得到所有的注解
//        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
//        for (Element element : elements) {
//            VariableElement variableElement = (VariableElement) element;
//            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
//            String fullClassName = classElement.getQualifiedName().toString();
//            ClassCreatorProxy proxy = mProxyMap.get(fullClassName);
//            if (proxy == null) {
//                proxy = new ClassCreatorProxy(mElementUtils, classElement,mMessager);
//                mProxyMap.put(fullClassName, proxy);
//            }
//            BindView bindAnnotation = variableElement.getAnnotation(BindView.class);
//            int id = bindAnnotation.value();
//            proxy.putElement(id, variableElement);
//        }
//        //通过遍历mProxyMap，创建java文件
//        for (String key : mProxyMap.keySet()) {
//            ClassCreatorProxy proxyInfo = mProxyMap.get(key);
//            JavaFile javaFile = JavaFile.builder(proxyInfo.getPackageName(), proxyInfo.generateJavaCode()).build();
//            try {
//                //　生成文件
//                javaFile.writeTo(processingEnv.getFiler());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        mMessager.printMessage(Diagnostic.Kind.NOTE, "process finish ....");
//        return true;
//    }
//}
