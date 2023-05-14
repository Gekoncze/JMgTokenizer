package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;

public @Entity class MultiLineCommentToken extends CommentToken {
    public MultiLineCommentToken() {
    }

    public MultiLineCommentToken(String text, int position) {
        super(text, position);
    }
}
