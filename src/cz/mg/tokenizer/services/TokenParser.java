package cz.mg.tokenizer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.utilities.CharacterReader;

public @Service interface TokenParser {
    @Optional Token parse(@Mandatory CharacterReader reader);
}
