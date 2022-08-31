package springbook.example;

public class FactoryMethodPattern {
    public static void main(String[] args) {
        Child1 child1 = new Child1();
        child1.conversation();
        /*
        안녕
        냐옹
        잘가
         */

        Child2 child2 = new Child2();
        child2.conversation();
        /*
        안녕
        왈
        잘가
         */
    }

    public static abstract class Super {

        public void conversation() {
            hello();
            Animal animal = getAnimal();
            animal.speak();
            bye();
        }

        public void hello() {
            System.out.println("안녕");
        }

        public void bye() {
            System.out.println("잘가");
        }

        // 팩토리 메서드
        protected abstract Animal getAnimal();

    }

    interface Animal {
        void speak();
    }

    public static class Child1 extends Super{

        @Override
        protected Animal getAnimal() {
            return new Cat();
        }

        public static class Cat implements Animal {

            @Override
            public void speak() {
                System.out.println("냐옹");
            }
        }
    }

    public static class Child2 extends Super{

        @Override
        protected Animal getAnimal() {
            return new Dog();
        }

        public static class Dog implements Animal {

            @Override
            public void speak() {
                System.out.println("왈");
            }
        }
    }
}
