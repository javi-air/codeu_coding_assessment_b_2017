// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.mathlang.impl;

import java.io.IOException;

import com.google.codeu.mathlang.core.tokens.Token;
import com.google.codeu.mathlang.core.tokens.NumberToken;
import com.google.codeu.mathlang.core.tokens.StringToken;
import com.google.codeu.mathlang.core.tokens.SymbolToken;
import com.google.codeu.mathlang.core.tokens.NameToken;
import com.google.codeu.mathlang.parsing.TokenReader;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.HashMap;

// MY TOKEN READER
//
// This is YOUR implementation of the token reader interface. To know how
// it should work, read src/com/google/codeu/mathlang/parsing/TokenReader.java.
// You should not need to change any other files to get your token reader to
// work with the test of the system.
public final class MyTokenReader implements TokenReader {

  private Scanner in;
  public HashMap<String, String> patterns;

  public MyTokenReader(String source) {
    // Your token reader will only be given a string for input. The string will
    // contain the whole source (0 or more lines).
    this.patterns = new HashMap<String, String>();
    patterns.put("[1-9].?[1-9]*", "number");
    patterns.put(".{1}", "symbol");
    patterns.put("[A-z][.]*", "name");
    patterns.put("[.]*", "string");
    this.in = new Scanner(source);
  }

  @Override
  public Token next() throws IOException {
    // Most of your work will take place here. For every call to |next| you should
    // return a token until you reach the end. When there are no more tokens, you
    // should return |null| to signal the end of input.

    // If for any reason you detect an error in the input, you may throw an IOException
    // which will stop all execution.
    if (!in.hasNext()){
      return null;
    }
    Token result = null;
    for (String pattern : patterns.keySet()){
      String input = in.next();
      if (Pattern.matches(pattern, input)){
        switch (patterns.get(pattern)) {
          case "number" :
            double dInput = Double.parseDouble(input);
            result = new NumberToken(dInput);
            break;
          case "symbol" : result = new SymbolToken(input.charAt(0)); break;
          case "name" : result = new NameToken(input); break;
          case "string" : result = new StringToken(input); break;
        }
      }
    }
    return result;
  }
}
