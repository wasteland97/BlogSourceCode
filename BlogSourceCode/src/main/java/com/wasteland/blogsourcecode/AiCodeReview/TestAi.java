package com.wasteland.blogsourcecode.AiCodeReview;

import com.wasteland.blogsourcecode.debugDemo.Student;
import org.assertj.core.util.Lists;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wfhstart
 * @create 2025-07-08
 */
@Component
public CodeReviewPromptTemplateLoader implements PromptTemplateLoader {
    private final Logger logger = LoggerFactory.getLogger(CodeReviewPromptTemplateLoader.class);

    private static final String PROMPT_FILE = "prompt_templates.yml";

    private final VelocityEngine velocityEngine;
    private final ObjectMapper yamlMapper;


    public CodeReviewPromptTemplateLoader() {
        // 初始化Velocity模板引擎
        this.velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath");
        velocityEngine.setProperty("resource.loader.classpath.class",
                ClasspathResourceLoader.class.getName());
        velocityEngine.init();

        // 初始化YAML解析器
        this.yamlMapper = new ObjectMapper(new YAMLFactory());
    }

    @Override
    public Map<String, Object> loadTemplates(String promptKey, String style) {
        try {
            // 从类路径加载YAML文件
            Resource resource = new ClassPathResource(PROMPT_FILE);
            try (InputStream inputStream = resource.getInputStream()) {
                // 解析YAML
                Map<String, Object> yamlData = yamlMapper.readValue(inputStream, Map.class);
                @SuppressWarnings("unchecked")
                Map<String, String> prompts = (Map<String, String>) yamlData.get(promptKey);

                if (prompts == null) {
                    throw new IllegalArgumentException("未找到指定的提示词配置: " + promptKey);
                }

                // 渲染模板
                String systemPrompt = renderTemplate(prompts.get(PromptTypeEnum.SYSTEM_PROMPT.value()), style);
                String userPrompt = renderTemplate(prompts.get(PromptTypeEnum.USER_PROMPT.value()), style);

                // 构建返回结构
                Map<String, Object> result = new HashMap<>();
                result.put("system_message",
                        Map.of("role", "system", "content", systemPrompt));
                result.put("user_message",
                        Map.of("role", "user", "content", userPrompt));

                return result;
            }
        } catch (IOException | IllegalArgumentException e) {
            logger.error("加载提示词配置失败", e);
            throw new RuntimeException("提示词配置加载失败: " + e.getMessage(), e);
        }
    }

    /**
     * 使用Velocity渲染模板
     */
    private String renderTemplate(String templateStr, String style) {
        if (templateStr == null) {
            return "";
        }

        VelocityContext context = new VelocityContext();
        // 设置模板变量
        context.put("style", style);
        context.put("diffs_text", "${diffs_text}");  // 保留占位符后续替换
        context.put("commits_text", "${commits_text}");

        // 使用StringWriter捕获输出
        StringWriter writer = new StringWriter();

        try {
            // 使用Velocity评估模板
            velocityEngine.evaluate(context, writer, "template", templateStr);

            // 处理可能存在的未闭合条件标签
            return writer.toString();
        } catch (Exception e) {
            logger.error("Failed to render template", e);
            return templateStr; // 返回原始模板作为回退
        }
    }

    
    public static void main(String[] args) {
        Student student = new Student();
        student.setAge(18);
        student.setName("张三");
        student.setAddress("浙江");
        Student student1 = new Student();
        student1.setAge(19);
        student1.setName("李四");
        student1.setAddress("湖北");

        List<Student> studentList = Lists.newArrayList(student, student1);

        List<String> nameList = studentList.stream().filter(Objects::nonNull)
                .filter(it -> it.getAge() > 18)
                .map(Student::getName)
                .collect(Collectors.toList());
        System.out.println(nameList);

        int count = count(55);
        System.out.println(count);
    }

    private static int count(int number) {
        int res = 0;
        for (int i = 0; i < number; i++) {
            if (i == 44) {
                res += i;
            }
        }
        return res;
    }

    private static int count1(int number) {
        int res = 0;
        for (int i = 0; i < number; i++) {
            if (i == 44) {
                res += i;
            }
        }
        return res;
    }

    private static int count2(int number) {
        int res = 0;
        for (int i = 0; i < number; i++) {
            if (i == 44) {
                res += i;
            }
        }
        return res;
    }

    private static int count3(int number) {
        int res = 0;
        for (int i = 0; i < number; i++) {
            if (i == 44) {
                res += i;
            }
        }
        return res;
    }

    private static int count4(int number) {
        int res = 0;
        for (int i = 0; i < number; i++) {
            if (i == 44) {
                res += i;
            }
        }
        return res;
    }

    private static int count5(int number) {
        int res = 0;
        for (int i = 0; i < number; i++) {
            if (i == 44) {
                res += i;
            }
        }
        return res;
    }

    private static int count6(int number) {
        int res = 0;
        for (int i = 0; i < number; i++) {
            if (i == 44) {
                res += i;
            }
        }
        return res;
    }
}
