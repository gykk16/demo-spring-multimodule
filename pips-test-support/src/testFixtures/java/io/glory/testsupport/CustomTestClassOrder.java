package io.glory.testsupport;

import java.util.Comparator;

import io.glory.testsupport.integrated.IntegratedLocalTestSupport;
import io.glory.testsupport.integrated.IntegratedTestSupport;
import org.junit.jupiter.api.ClassDescriptor;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.ClassOrdererContext;

/**
 * 테스트 클래스 실행 순서를 지정 클래스
 */
public class CustomTestClassOrder implements ClassOrderer {

    @Override
    public void orderClasses(ClassOrdererContext context) {
        context.getClassDescriptors().sort(Comparator.comparingInt(this::getOrder));
    }

    private int getOrder(ClassDescriptor descriptor) {
        if (descriptor.getTestClass().getSuperclass().equals(IntegratedLocalTestSupport.class)) {
            return 1;
        } else if (descriptor.getTestClass().getSuperclass().equals(IntegratedTestSupport.class)) {
            return 2;
        }
        return 3;
    }

}
