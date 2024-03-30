package cz.mg.tokenizer.components;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.token.Token;

public @Component interface TokenParser {
    @Optional Token parse(@Mandatory CharacterReader reader);
}
