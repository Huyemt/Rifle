package org.bullet.frontend;

import org.bullet.frontend.tokens.Token;
import org.bullet.frontend.tokens.TokenKind;
import org.bullet.frontend.tokens.VToken;

import java.io.File;

/**
 * 词法分析器<br><br>
 *
 * Lexer
 *
 * @author Huyemt
 */

public class Lexer extends ILexer {
    public Lexer(String source) {
        super(source);
    }

    public Lexer(File source) throws Exception {
        super(source);
    }

    @Override
    public final void next() {
        while (location.current == '\n' || location.current == '\t' || location.current == ' ') {
            this.location.next();
        }

        switch (this.location.current) {
            case '\0':
                this.makeToken(TokenKind.EOF);
                break;
            case '+':
                this.makeToken(TokenKind.PLUS);
                this.location.next();
                break;
            case '-':
                this.makeToken(TokenKind.MINUS);
                this.location.next();
                break;
            case '*':
                this.makeToken(TokenKind.STAR);
                this.location.next();
                break;
            case '/':
                this.makeToken(TokenKind.SLASH);
                this.location.next();
                break;
            default:
                if (Character.isDigit(this.location.current)) {
                    this.makeNumber();
                    break;
                }
                break;
        }
    }

    @Override
    public void makeToken(TokenKind kind) {
        this.current = new Token(kind);
    }

    @Override
    public void makeToken(TokenKind kind, String value) {
        this.current = new VToken(kind, value);
    }

    public void makeNumber() {
        int start = this.location.index;
        boolean flag = false;

        while (Character.isDigit(this.location.current) || this.location.current == '.') {
            if (this.location.current == '.') {
                if (!flag) {
                    flag = true;
                    continue;
                }
                //throw
            }

            this.location.next();
        }

        this.makeToken(TokenKind.VT_NUMBER, this.location.source.substring(start, this.location.index));
    }
}
