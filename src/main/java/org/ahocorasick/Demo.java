package org.ahocorasick;

import java.util.Collection;
import java.util.StringTokenizer;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Token;
import org.ahocorasick.trie.Trie;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;

public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Trie trie = Trie.builder().ignoreCase()
				// .allowOverlaps()
				.ignoreStopwords().filterRepeatChars()
				// .stopOnHit()
				.addKeyword("鸡&&&巴").addKeyword("洪志").addKeyword("洪志明").addKeyword("李洪志").addKeyword("习近平")
				.addKeyword("fuuccck").addStopword('&').addStopword('*').build();

		//Collection<Emit> emits = trie.parseText("天堂来的孩子我们提屁股鸡&&*&巴习近平腾讯李洪志明FFfuuccckkk");

		Collection<Token> tokens = trie.tokenize("天堂来的孩子我们提屁股鸡&&&巴习近平腾讯李洪志明FFfuuccckkk");

		StringBuffer html = new StringBuffer();
		for (Token token : tokens) {
			if (token.isMatch()) {
				html.append("<i>");
			}
			html.append(token.getFragment());
			if (token.isMatch()) {
				html.append("</i>");
			}
		}
		//System.out.println(html);
		
		
		FelEngine fel = new FelEngineImpl();  
		FelContext ctx = fel.getContext();  
		ctx.set("男", true);  
		ctx.set("女", false);  
		ctx.set("公关", true);  
		Object result = fel.eval("(男||女)&&公关");  
		//System.out.println(result);  
		
		

            String str="(男||女)&&公关";
            StringTokenizer st=new StringTokenizer(str,"()&|");
            while(st.hasMoreTokens()) { 
               System.out.println(st.nextToken());
            }
            
            
/*            String[] tt=str.split(",");
            for(String s:tt)
            {
                System.out.println(s);
            }*/
        

	}

}
