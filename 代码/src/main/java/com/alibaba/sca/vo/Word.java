package com.alibaba.sca.vo;

public class Word
{
  private String word;
  private String chinese;

  public Word(String word,String chinese)
  {
      this.word = word;
      this.chinese = chinese;
  }

  public String getWord()
  {
    return this.word;
  }

  public Word setWord(String value)
  {
    this.word = value;
    return this;
  }

  public String getChinese()
  {
    return this.chinese;
  }

  public Word setChinese(String value)
  {
    this.chinese = value;
    return this;
  }
}