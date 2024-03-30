package cz.mg.tokenizer.entities.tokens.comment;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.tokens.CommentToken;

public @Entity class SingleLineCommentToken extends CommentToken {
    public SingleLineCommentToken() {
    }

    public SingleLineCommentToken(String text, int position) {
        super(text, position);
    }
}
