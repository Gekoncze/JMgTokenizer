package cz.mg.tokenizer;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.tokenizer.entities.Token;

public @Component interface Tokenizer {
    @Mandatory List<Token> tokenize(@Mandatory String content);
}
