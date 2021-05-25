package com.pplovely.processortest;


import com.google.auto.service.AutoService;
import com.pplovely.anotationrouter.RouterManager;
import com.pplovely.anotationrouter.TestRouter;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;




@AutoService(Processor.class)
public class TestRouterProcessor extends AbstractProcessor {

    public static final String ROOT_INIT = "com.pplovely.anotationroter";
    public static final String INIT_CLASS = "TestRouterInit";
    public static final String INIT_METHOD = "init";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }


    //这个方法非常必要，否则将不会执行到process()方法
    @Override public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(TestRouter.class.getCanonicalName());
    }

    @Override public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        System.out.println("TestRouterProcessor process");
        if (annotations == null || annotations.isEmpty()) {
            return false;
        }
        for (Element element : env.getElementsAnnotatedWith(TestRouter.class)) {
            //获取注解中的内容
            String className = element.getSimpleName().toString();
            String uri = element.getAnnotation(TestRouter.class).url();


            try {
                //使用javapoet来动态生成代码
                MethodSpec main = MethodSpec.methodBuilder(INIT_METHOD)
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(void.class)
                        .addStatement("$T.getInstance().register($S,$S)", RouterManager.class, className, uri)
                        .build();

                TypeSpec testRouterInit = TypeSpec.classBuilder(INIT_CLASS)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addMethod(main)
                        .build();

                JavaFile javaFile = JavaFile.builder(ROOT_INIT, testRouterInit)
                        .build();


                Filer filer = processingEnv.getFiler();
                javaFile.writeTo(filer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

}
