package cz.mg.tokenizer.utilities;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.tokenizer.entities.Token;

public @Utility interface TokenFactory {
    @Mandatory Token create(@Mandatory String text, int position);
}
