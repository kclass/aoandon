package com.hk.test.designmodel.proxy;

/**
 * @author kai.hu
 * @date 2020/10/27 10:40
 */
public class SubjectProxy {
    private Subject subject;

    public void request() {
        if (subject == null) {
            subject = new RealSubject();
        }
        preRequest();
        subject.request();
        afterRequest();
    }

    private void preRequest() {
        System.out.println("我要在请求前说句话");
    }

    private void afterRequest() {
        System.out.println("我要在请求后说句话");
    }
}
