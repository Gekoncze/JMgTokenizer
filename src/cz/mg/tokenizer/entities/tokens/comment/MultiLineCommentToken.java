package cz.mg.tokenizer.entities.tokens.comment;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.tokens.CommentToken;

public @Entity class MultiLineCommentToken extends CommentToken {
    public MultiLineCommentToken() {
    }

    public MultiLineCommentToken(String text, int position) {
        super(text, position);
    }
}
