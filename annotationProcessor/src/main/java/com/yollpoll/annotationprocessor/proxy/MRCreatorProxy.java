package com.yollpoll.annotationprocessor.proxy;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;

/**
 * Created by spq on 2021/3/31
 */
public class MRCreatorProxy extends CreatorProxy {
    public  List<Element> list = new ArrayList<>();

    public MRCreatorProxy(Elements elementUtils, Element classElement, Messager messager) {
        super(elementUtils, classElement, messager);
    }


    public void add(Element element) {
        list.add(element);
    }


    @Override
    public TypeSpec generateJavaCode() {
        TypeSpec bindingClass = TypeSpec.classBuilder(getClassName())
                .addModifiers(Modifier.PUBLIC)
                .addFields(generateFieldSpec())
                .build();
        return bindingClass;
    }

    /**
     * getEnclosingElement 表示包裹当前element的element
     * @return 变量名
     */
    public List<FieldSpec> generateFieldSpec() {
        List<FieldSpec> fieldSpecs = new ArrayList<>();
        for (Element element : list) {
            FieldSpec fieldSpec = FieldSpec.builder(String.class, element.getEnclosingElement().getSimpleName()+"_"+element.getSimpleName().toString(), Modifier.PUBLIC, Modifier.STATIC,
                    Modifier.FINAL)
                    .initializer("$S",element.getEnclosingElement().getSimpleName()+"&&"+element.getSimpleName().toString())
                    .build();
            fieldSpecs.add(fieldSpec);
        }
        return fieldSpecs;
    }

    @Override
    public String getClassName() {
        return "MR";
    }

    @Override
    public String getPackageName() {
        return super.mPackageName;
    }
}
