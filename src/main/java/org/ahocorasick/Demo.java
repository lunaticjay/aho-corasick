package org.ahocorasick;

import java.util.Collection;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Token;
import org.ahocorasick.trie.Trie;

public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Trie trie = Trie.builder().ignoreCase()
				// .allowOverlaps()
				.ignoreStopwords().filterRepeatChars()
				// .stopOnHit()
				.addKeyword("鸡巴").addKeyword("洪志").addKeyword("洪志明").addKeyword("李洪志").addKeyword("习近平")
				.addKeyword("fuck").addStopword('&').addStopword('*').build();

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
		System.out.println(html);
	}

}
