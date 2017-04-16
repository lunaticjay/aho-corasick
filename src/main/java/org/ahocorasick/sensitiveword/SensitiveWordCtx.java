package org.ahocorasick.sensitiveword;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Token;
import org.ahocorasick.trie.Trie;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;

public class SensitiveWordCtx {

	private static List<SensitiveWord> sensitiveWords = new ArrayList<>();

	private static Map<String, List<SensitiveWord>> tokenSensitiveWordsMap = new HashMap<String, List<SensitiveWord>>();

	public static void init() {
		importSensitiveWords();
		generateTokenMap();
	}

	private static void importSensitiveWords() {
		sensitiveWords.add(new SensitiveWord("李洪志", "三级", "政治", "1"));
		sensitiveWords.add(new SensitiveWord("习近平", "三级", "政治", "1"));
		sensitiveWords.add(new SensitiveWord("鸡巴", "二级", "色情", "1"));
		sensitiveWords.add(new SensitiveWord("fuck", "一级", "攻击", "1"));
		sensitiveWords.add(new SensitiveWord("别墅&&首付", "一级", "广告", "2"));
		sensitiveWords.add(new SensitiveWord("私借||私贷", "二级", "广告", "2"));
		sensitiveWords.add(new SensitiveWord("(男||女)&&公关", "二级", "广告", "2"));
	}

	private static void generateTokenMap() {
		for (SensitiveWord word : sensitiveWords) {
			StringTokenizer st = new StringTokenizer(word.getText(), "()&|");
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				if (tokenSensitiveWordsMap.get(token) == null) {
					tokenSensitiveWordsMap.put(token, new ArrayList<SensitiveWord>());
				}
				tokenSensitiveWordsMap.get(token).add(word);
			}
		}
	}

	public static Set<String> getSensitiveTokens() {
		return tokenSensitiveWordsMap.keySet();
	}

	public static void proccessExpressEmits(final List<Emit> collectedEmits) {
		final List<Emit> addEmits = new ArrayList<>();

		final Set<SensitiveWord> expressSensitiveWords = new HashSet<>();

		final Set<Emit> expressEmits = new HashSet<>();

		final Map<String, Emit> expressTokenMaps = new HashMap<>();

		for (final Emit emit : collectedEmits) {

			final List<SensitiveWord> sensitiveWords = tokenSensitiveWordsMap.get(emit.getKeyword());

			final List<SensitiveWord> currExpressWords = new ArrayList<>();

			boolean hasNonExpressSensitiveWord = false;
			for (final SensitiveWord word : sensitiveWords) {
				if (word.getType().equals("1")) {
					hasNonExpressSensitiveWord = true;
					break;
				} else {
					currExpressWords.add(word);
				}
			}

			if (!hasNonExpressSensitiveWord) {
				expressEmits.add(emit);
				expressTokenMaps.put(emit.getKeyword(), emit);
				expressSensitiveWords.addAll(currExpressWords);
			}
		}
		collectedEmits.removeAll(expressEmits);

		for (final SensitiveWord word : expressSensitiveWords) {
			FelEngine fel = new FelEngineImpl();
			FelContext ctx = fel.getContext();
			String text = word.getText();
			StringTokenizer tokenizer = new StringTokenizer(text, "()&|");
			List<Emit> currExpressEmits = new ArrayList<Emit>();
			while (tokenizer.hasMoreElements()) {
				String token = tokenizer.nextToken();
				Emit emit = expressTokenMaps.get(token);
				ctx.set(token, null != emit);
				if (null != emit) {
					currExpressEmits.add(emit);
				}
			}
			Boolean isMatched = (Boolean) fel.eval(text);

			if (isMatched) {
				addEmits.addAll(currExpressEmits);
			}
		}

		collectedEmits.addAll(addEmits);

	}

	public static void main(String[] args) {
		init();
		Trie trie = Trie.builder().ignoreCase()
				// .allowOverlaps()
				.ignoreStopwords().filterRepeatChars()
				// .stopOnHit()
				.addKeywords(getSensitiveTokens()).addStopword('&').addStopword('*').build();

		// Collection<Emit> emits =
		// trie.parseText("天堂来私借的女孩子公关我们提屁股鸡&&&巴习近平腾讯李洪志明FFfuuccckkk");

		Collection<Token> tokens = trie.tokenize("天堂来私借的女孩别墅子公关我们提屁股鸡&&&巴习近平腾讯首付李洪志明FFfuuccckkk");

		StringBuffer html = new StringBuffer();
		for (Token token : tokens) {
			if (token.isMatch()) {
				html.append("<i>");
				for (int i = 0; i < token.getFragment().length(); i++) {
					html.append('*');
				}
			} else {
				html.append(token.getFragment());
			}
			if (token.isMatch()) {
				html.append("</i>");
			}
		}

		System.out.println(html);
	}

}
