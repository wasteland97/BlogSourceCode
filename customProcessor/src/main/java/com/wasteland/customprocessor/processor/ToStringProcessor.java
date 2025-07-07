package com.wasteland.customprocessor.processor;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Names;
import com.wasteland.customprocessor.annotation.ToString;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author wasteland
 * @create 2025-03-04
 */
@SupportedAnnotationTypes("com.wasteland.customprocessor.annotation.ToString") // 指定支持的注解类型
@SupportedSourceVersion(javax.lang.model.SourceVersion.RELEASE_8) // 指定支持的java版本
public class ToStringProcessor extends AbstractProcessor {

    protected Messager messager;   // 用来在编译期打log用的
    protected JavacTrees trees;    // 提供了待处理的抽象语法树
    protected TreeMaker treeMaker; // 封装了创建AST节点的一些方法
    protected Names names;         // 提供了创建标识符的方法
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        processingEnv = jbUnwrap(ProcessingEnvironment.class, processingEnv);
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.trees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    /**
     * 获取 IDEA 环境下的 ProcessingEnvironment
     */
    private static <T> T jbUnwrap(Class<? extends T> iface, T wrapper) {
        T unwrapped = null;
        try {
            final Class<?> apiWrappers = wrapper.getClass().getClassLoader().loadClass("org.jetbrains.jps.javac.APIWrappers");
            final Method unwrapMethod = apiWrappers.getDeclaredMethod("unwrap", Class.class, Object.class);
            unwrapped = iface.cast(unwrapMethod.invoke(null, iface, wrapper));
        }
        catch (Throwable ignored) {}
        return unwrapped != null? unwrapped : wrapper;
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 获取被 @ToString 注解修饰的元素
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(ToString.class);
        set.forEach(element -> {
            // 根据元素获取对应的语法树 JCTree
            JCTree jcTree = trees.getTree(element);
            jcTree.accept(new TreeTranslator() {
                // 处理语法树的类定义部分 JCClassDecl
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                    // 检查是否已经存在 toString 方法
                    if (!hasToStringMethod(jcClassDecl)) {
                        // 创建 toString 方法
                        createToStringMethod(jcClassDecl);
                    }
                    super.visitClassDef(jcClassDecl);
                }
            });
        });
        return true;
    }

    private boolean hasToStringMethod(JCTree.JCClassDecl jcClassDecl) {
        for (JCTree tree : jcClassDecl.defs) {
            if (tree.getKind().equals(Tree.Kind.METHOD)) {
                JCTree.JCMethodDecl methodDecl = (JCTree.JCMethodDecl) tree;
                if (methodDecl.getName().contentEquals("toString")) {
                    return true;
                }
            }
        }
        return false;
    }
    private void createToStringMethod(JCTree.JCClassDecl classDecl) {
        // 构建toString()的方法体
        JCTree.JCBlock methodBody = buildToStringMethodBody(classDecl);

        // 创建toString()方法
        JCTree.JCMethodDecl toStringMethod = treeMaker.MethodDef(
                treeMaker.Modifiers(1), // public
                names.fromString("toString"), // 方法名
                treeMaker.Ident(names.fromString("String")), // 返回类型
                List.nil(), // 泛型参数
                List.nil(), // 参数列表
                List.nil(), // 异常列表
                methodBody, // 方法体
                null // 默认值（用于注解方法）
        );

        // 将toString()方法添加到类中
        classDecl.defs = classDecl.defs.append(toStringMethod);
    }

    private JCTree.JCBlock buildToStringMethodBody(JCTree.JCClassDecl classDecl) {
        // 构建方法体的语句
        List<JCTree.JCStatement> statements = List.nil();

        // 开始构建返回值
        JCTree.JCExpression returnValue = treeMaker.Literal(classDecl.name + "{");

        // 遍历类的字段
        boolean firstField = true;
        for (JCTree def : classDecl.defs) {
            if (def instanceof JCTree.JCVariableDecl) {
                JCTree.JCVariableDecl field = (JCTree.JCVariableDecl) def;
                // 添加字段名和值
                if (!firstField) {
                    returnValue = treeMaker.Binary(
                            JCTree.Tag.PLUS,
                            returnValue,
                            treeMaker.Literal(", ")
                    );
                }
                returnValue = treeMaker.Binary(
                        JCTree.Tag.PLUS,
                        returnValue,
                        treeMaker.Binary(
                                JCTree.Tag.PLUS,
                                treeMaker.Literal(field.name + "="),
                                treeMaker.Ident(field.name)
                        )
                );
                firstField = false;
            }
        }

        // 添加 "}"
        returnValue = treeMaker.Binary(
                JCTree.Tag.PLUS,
                returnValue,
                treeMaker.Literal("}")
        );

        // 创建 return 语句
        JCTree.JCReturn returnStatement = treeMaker.Return(returnValue);
        statements = statements.append(returnStatement);

        // 创建方法体
        return treeMaker.Block(0, statements);
    }

}

    
