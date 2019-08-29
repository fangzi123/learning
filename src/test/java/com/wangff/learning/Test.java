package com.wangff.learning;

import com.wangff.learning.designpatterns.chain.CaseChain;
import com.wangff.learning.designpatterns.chain.OneCase;
import com.wangff.learning.designpatterns.chain.TwoCase;

/**
 * @author wangff
 * @date 2019/8/29 18:10
 */
public class Test {
    public static void main(String[] args) {
        CaseChain caseChain = new CaseChain();
        caseChain.addBaseCase(new OneCase());
        caseChain.addBaseCase(new TwoCase());
        caseChain.doSomething("123",caseChain);

    }
}
