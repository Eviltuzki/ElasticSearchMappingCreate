package top.eviltuzki.plugin.es.enums;

public enum  IndexOptions {

    /**
     * Only the doc number is indexed. Can answer the question Does this term exist in this field?
     */
    DOCS,
    /**
     * Doc number and term frequencies are indexed. Term frequencies are used to score repeated terms higher than single terms.
     */
    FREQS,
    /**
     * Doc number, term frequencies, and term positions (or order) are indexed. Positions can be used for proximity or phrase queries.
     */
    POSITIONS,
    /**
     * Doc number, term frequencies, positions, and start and end character offsets (which map the term back to the original string) are indexed. Offsets are used by the postings highlighter.
     */
    OFFSETS
}
