package org.ahocorasick.trie;

public class TrieConfig {

    private boolean allowOverlaps = false;

    private boolean onlyWholeWords = false;

    private boolean onlyWholeWordsWhiteSpaceSeparated = false;

    private boolean caseInsensitive = false;

    private boolean stopOnHit = false;
    
    private boolean filterRepeatChars = false;
    
    private boolean ignoreStopwords = false;

    public boolean isStopOnHit() {
        return stopOnHit;
    }

    public void setStopOnHit(boolean stopOnHit) {
        this.stopOnHit = stopOnHit;
    }

    public boolean isAllowOverlaps() {
        return allowOverlaps;
    }

    public void setAllowOverlaps(boolean allowOverlaps) {
        this.allowOverlaps = allowOverlaps;
    }

    public boolean isOnlyWholeWords() {
        return onlyWholeWords;
    }

    public void setOnlyWholeWords(boolean onlyWholeWords) {
        this.onlyWholeWords = onlyWholeWords;
    }

    public boolean isOnlyWholeWordsWhiteSpaceSeparated() {
        return onlyWholeWordsWhiteSpaceSeparated;
    }

    public void setOnlyWholeWordsWhiteSpaceSeparated(boolean onlyWholeWordsWhiteSpaceSeparated) {
        this.onlyWholeWordsWhiteSpaceSeparated = onlyWholeWordsWhiteSpaceSeparated;
    }

    public boolean isCaseInsensitive() {
        return caseInsensitive;
    }

    public void setCaseInsensitive(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
    }

	public boolean isFilterRepeatChars() {
		return filterRepeatChars;
	}

	public void setFilterRepeatChars(boolean filterRepeatChars) {
		this.filterRepeatChars = filterRepeatChars;
	}

	public boolean isIgnoreStopwords() {
		return ignoreStopwords;
	}

	public void setIgnoreStopwords(boolean ignoreStopwords) {
		this.ignoreStopwords = ignoreStopwords;
	}

}
