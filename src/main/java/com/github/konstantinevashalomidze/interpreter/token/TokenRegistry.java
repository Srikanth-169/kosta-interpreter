package com.github.konstantinevashalomidze.interpreter.token;

import com.github.konstantinevashalomidze.interpreter.token.types.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum TokenRegistry {
    INSTANCE;

    private final Map<String, Token> literalToTokens = new HashMap<>();
    TokenRegistry() {
        literalToTokens.put(And.INSTANCE.literal(), And.INSTANCE);
        literalToTokens.put(Assign.INSTANCE.literal(), Assign.INSTANCE);
        literalToTokens.put(Asterisk.INSTANCE.literal(), Asterisk.INSTANCE);
        literalToTokens.put(Bang.INSTANCE.literal(), Bang.INSTANCE);
        literalToTokens.put(Comma.INSTANCE.literal(), Comma.INSTANCE);
        literalToTokens.put(Definition.INSTANCE.literal(), Definition.INSTANCE);
        literalToTokens.put(Else.INSTANCE.literal(), Else.INSTANCE);
        literalToTokens.put(Eof.INSTANCE.literal(), Eof.INSTANCE);
        literalToTokens.put(Eq.INSTANCE.literal(), Eq.INSTANCE);
        literalToTokens.put(False.INSTANCE.literal(), False.INSTANCE);
        literalToTokens.put(Function.INSTANCE.literal(), Function.INSTANCE);
        literalToTokens.put(Gt.INSTANCE.literal(), Gt.INSTANCE);
        literalToTokens.put(If.INSTANCE.literal(), If.INSTANCE);
        literalToTokens.put(Illegal.INSTANCE.literal(), Illegal.INSTANCE);
        literalToTokens.put(Lb.INSTANCE.literal(), Lb.INSTANCE);
        literalToTokens.put(Lp.INSTANCE.literal(), Lp.INSTANCE);
        literalToTokens.put(Lt.INSTANCE.literal(), Lt.INSTANCE);
        literalToTokens.put(MinusInfix.INSTANCE.literal(), MinusInfix.INSTANCE);
        literalToTokens.put(MinusPrefix.INSTANCE.literal(), MinusPrefix.INSTANCE);
        literalToTokens.put(NotEq.INSTANCE.literal(), NotEq.INSTANCE);
        literalToTokens.put(Or.INSTANCE.literal(), Or.INSTANCE);
        literalToTokens.put(Plus.INSTANCE.literal(), Plus.INSTANCE);
        literalToTokens.put(Return.INSTANCE.literal(), Return.INSTANCE);
        literalToTokens.put(Rp.INSTANCE.literal(), Rp.INSTANCE);
        literalToTokens.put(Semicolon.INSTANCE.literal(), Semicolon.INSTANCE);
        literalToTokens.put(Slash.INSTANCE.literal(), Slash.INSTANCE);
        literalToTokens.put(True.INSTANCE.literal(), True.INSTANCE);
        literalToTokens.put(Variable.INSTANCE.literal(), Variable.INSTANCE);
    }

    public Optional<Token> getToken(String literal) {
        return Optional.ofNullable(literalToTokens.get(literal));
    }

}