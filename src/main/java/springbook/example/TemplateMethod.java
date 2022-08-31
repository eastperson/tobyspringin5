package springbook.example;

public class TemplateMethod {

    public static void main(String[] args) {
        Child1 child1 = new Child1();
        child1.templateMethod();
        /*
        default method
        hook method
        child1 method
         */

        Child2 child2 = new Child2();
        child2.templateMethod();
        /*
        default method
        hook method
        child2 method
         */
    }

    public static abstract class Super {
        public void templateMethod() {
            // 기본 알고리즘 코드
            defaultMethod();
            hookMethod();
            abstractMethod();
        }

        private void defaultMethod() {
            System.out.println("default method");
        }

        // 선택적으로 오버라이드 가능한 훅 메소드
        protected void hookMethod() {
            System.out.println("hook method");
        }

        public abstract void abstractMethod();
    }

    public static class Child1 extends Super {

        @Override
        public void abstractMethod() {
            System.out.println("child1 method");
        }
    }

    public static class Child2 extends Super {

        @Override
        public void abstractMethod() {
            System.out.println("child2 method");
        }
    }
}
