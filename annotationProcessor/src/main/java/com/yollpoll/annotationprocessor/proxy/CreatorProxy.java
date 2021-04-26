package com.yollpoll.annotationprocessor.proxy;

import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.util.Elements;

/**
 * Created by spq on 2021/3/31
 */
public abstract class CreatorProxy {
    protected String mPackageName;
    protected Element mTypeElement;
    protected Messager messager;

    public CreatorProxy(Elements elementUtils, Element classElement, Messager messager) {
        this.mTypeElement = classElement;
        this.messager=messager;
        PackageElement packageElement = elementUtils.getPackageOf(mTypeElement);
        String packageName = packageElement.getQualifiedName().toString();
        this.mPackageName = packageName;
    }

    /**
     * 创建Java代码
     *
     * @return 生成java代码
     */
    public abstract TypeSpec generateJavaCode();

    /**
     * @return 包名
     */
    public String getPackageName() {
        return mPackageName;
    }

    /**
     * @return 类名
     */
    public abstract String getClassName();
}
