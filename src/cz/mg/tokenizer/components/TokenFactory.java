package cz.mg.tokenizer.components;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.token.Token;

public @Component interface TokenFactory {
    @Mandatory Token create(@Mandatory String text, int position);
}
