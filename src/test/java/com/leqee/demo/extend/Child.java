package com.leqee.demo.extend;

// 继承时方法修饰限定符只能
// 1)保持不变
// 或者2)按照default<protected<public的顺序向范围更大的一个限定符变化
public class Child extends Parent {

    @Override
    protected void testProtected() {
        super.testProtected();
    }

    @Override
    public void testDefault() {
        super.testDefault();
    }

    @Override
    public void testPublic() {
        super.testPublic();
    }

}
