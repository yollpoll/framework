package com.yollpoll.annotationprocessor.proxy;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by spq on 2021/3/31
 */
public class ClassCreatorProxy extends CreatorProxy{
    private Map<Integer, VariableElement> mVariableElementMap = new HashMap<>();

    public ClassCreatorProxy(Elements elementUtils, TypeElement classElement, Messager messager) {
        super(elementUtils, classElement,messager);
    }

    public void putElement(int id, VariableElement element) {
        mVariableElementMap.put(id, element);
    }


    /**
     * 创建Java代码
     */
    @Override
    public TypeSpec generateJavaCode() {
        TypeSpec bindingClass = TypeSpec.classBuilder(getClassName())
                .addModifiers(Modifier.PUBLIC)
                .addMethod(generateMethods())
                .build();
        return bindingClass;

    }
    /**
     * 加入Method
     */

    private MethodSpec generateMethods() {
        ClassName host = ClassName.bestGuess(((TypeElement)mTypeElement).getQualifiedName().toString());
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(host, "host");

        for (int id : mVariableElementMap.keySet()) {
            VariableElement element = mVariableElementMap.get(id);
            String name = element.getSimpleName().toString();
            String type = element.asType().toString();
            methodBuilder.addCode("host." + name + " = " + "(" + type + ")(((android.app.Activity)host).findViewById( " + id + "));");
        }
        return methodBuilder.build();
    }

    @Override
    public String getClassName() {
        return mTypeElement.getSimpleName().toString()+"_ViewBinding";
    }
}
