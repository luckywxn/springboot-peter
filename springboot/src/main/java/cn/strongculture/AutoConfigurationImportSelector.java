package cn.strongculture;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.*;

public class AutoConfigurationImportSelector implements DeferredImportSelector {

    private ClassLoader beanClassLoader;
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        AutoConfigurationEntry autoConfigurationEntry = getAutoConfigurationEntry(annotationMetadata);
        return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
    }

    private AutoConfigurationEntry getAutoConfigurationEntry(AnnotationMetadata annotationMetadata) {

        AnnotationAttributes attributes = getAttributes(annotationMetadata);
        List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);

        Set<String> exclusions = getExclusions(annotationMetadata, attributes);
        return new AutoConfigurationEntry(configurations, exclusions);
    }

    private Set<String> getExclusions(AnnotationMetadata annotationMetadata, AnnotationAttributes attributes) {
        Set<String> excluded = new LinkedHashSet<>();
        excluded.addAll(asList(attributes, "exclude"));
        excluded.addAll(Arrays.asList(attributes.getStringArray("excludeName")));
//        excluded.addAll(getExcludeAutoConfigurationsProperty());
        return excluded;
    }

    private List<String> getCandidateConfigurations(AnnotationMetadata annotationMetadata, AnnotationAttributes attributes) {
        List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),
                getBeanClassLoader());
        Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
                + "are using a custom packaging, make sure that file is correct.");
        return configurations;
    }

    private AnnotationAttributes getAttributes(AnnotationMetadata metadata) {

        String name = getAnnotationClass().getName();
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(name, true));
        Assert.notNull(attributes, () -> "No auto-configuration attributes found. Is " + metadata.getClassName()
                + " annotated with " + ClassUtils.getShortName(name) + "?");
        return attributes;
    }

    private Class<?> getAnnotationClass() {
        return EnableAutoConfiguration.class;
    }

    protected Class<?> getSpringFactoriesLoaderFactoryClass() {
        return EnableAutoConfiguration.class;
    }

    protected ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    protected final List<String> asList(AnnotationAttributes attributes, String name) {
        String[] value = attributes.getStringArray(name);
        return Arrays.asList(value);
    }

    protected static class AutoConfigurationEntry {

        private final List<String> configurations;

        private final Set<String> exclusions;

        private AutoConfigurationEntry() {
            this.configurations = Collections.emptyList();
            this.exclusions = Collections.emptySet();
        }

        /**
         * Create an entry with the configurations that were contributed and their
         * exclusions.
         * @param configurations the configurations that should be imported
         * @param exclusions the exclusions that were applied to the original list
         */
        AutoConfigurationEntry(Collection<String> configurations, Collection<String> exclusions) {
            this.configurations = new ArrayList<>(configurations);
            this.exclusions = new HashSet<>(exclusions);
        }

        public List<String> getConfigurations() {
            return this.configurations;
        }

        public Set<String> getExclusions() {
            return this.exclusions;
        }

    }
}
