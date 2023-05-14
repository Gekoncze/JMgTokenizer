package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;

public @Entity class SingleLineCommentToken extends CommentToken {
    public SingleLineCommentToken() {
    }

    public SingleLineCommentToken(String text, int position) {
        super(text, position);
    }
}
