package io.glory.module.http.webclient;

import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.reactivestreams.Subscription;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Operators;
import reactor.util.context.Context;

/**
 * Reactor Context 를 MDC 로 복사해 주는 Configuration
 */
@Configuration
public class MdcContextLifterConfig {

    public static final String MDC_CONTEXT_REACTOR_KEY = MdcContextLifterConfig.class.getName();

    @PostConstruct
    public void contextOperatorHook() {
        Hooks.onEachOperator(MDC_CONTEXT_REACTOR_KEY,
                Operators.lift((scannable, subscriber) -> new MdcContextLifter<>(subscriber)));
    }

    @PreDestroy
    public void cleanupHook() {
        Hooks.resetOnEachOperator(MDC_CONTEXT_REACTOR_KEY);
    }

    /**
     * onNext 호출 시 Reactor.Context 상태를 MDC 로 복사해 주는 Helper
     *
     * @param <T>
     */
    public static class MdcContextLifter<T> implements CoreSubscriber<T> {

        private final CoreSubscriber<T> coreSubscriber;

        public MdcContextLifter(CoreSubscriber<T> coreSubscriber) {
            this.coreSubscriber = coreSubscriber;
        }

        @Override
        public void onSubscribe(Subscription subscription) {
            coreSubscriber.onSubscribe(subscription);
        }

        @Override
        public void onNext(T t) {
            copyToMdc(coreSubscriber.currentContext());
            coreSubscriber.onNext(t);
        }

        @Override
        public void onError(Throwable throwable) {
            coreSubscriber.onError(throwable);
        }

        @Override
        public void onComplete() {
            coreSubscriber.onComplete();
        }

        @Override
        public Context currentContext() {
            return coreSubscriber.currentContext();
        }

        /**
         * Context 를 MDC 로 복사한다. <br>
         * Context 가 비어 있을 시 MDC 를 clear 한다 <br>
         * 이 메서드가 호출된 후 MDC 의 상태는 Reactor.Context 와 동일해진다
         */
        void copyToMdc(Context context) {
            if (context != null && !context.isEmpty()) {
                Map<String, String> map = context.stream()
                        .collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString()));
                MDC.setContextMap(map);
            } else {
                MDC.clear();
            }
        }

    }

}