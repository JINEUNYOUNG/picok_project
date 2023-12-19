package com.tjoeun.vo;

import java.util.Date;

public class ReplyVO {
   
   private int board_idx;
   private int comment_idx;
   private int reply_idx;
   private String nickname;
   private String id;
   private String reply_content;
   private Date reply_ceg_Date;
   
   public ReplyVO() {}
   
   //   getters & setters
   public int getBoard_idx() {
      return board_idx;
   }
   public void setBoard_idx(int board_idx) {
      this.board_idx = board_idx;
   }
   public int getComment_idx() {
      return comment_idx;
   }
   public void setComment_idx(int comment_idx) {
      this.comment_idx = comment_idx;
   }
   public int getReply_idx() {
      return reply_idx;
   }
   public void setReply_idx(int reply_idx) {
      this.reply_idx = reply_idx;
   }
   public String getId() {
      return id;
   }
   public void setId(String id) {
      this.id = id;
   }
   public String getReply_content() {
      return reply_content;
   }
   public void setReply_content(String reply_content) {
      this.reply_content = reply_content;
   }
   public Date getReply_ceg_Date() {
      return reply_ceg_Date;
   }
   public void setReply_ceg_Date(Date reply_ceg_Date) {
      this.reply_ceg_Date = reply_ceg_Date;
   }
   public String getNickname() {
      return nickname;
   }
   public void setNickname(String nickname) {
      this.nickname = nickname;
   }

   //   toString()
   @Override
   public String toString() {
      return "ReplyVO [board_idx=" + board_idx + ", comment_idx=" + comment_idx + ", reply_idx=" + reply_idx
            + ", nickname=" + nickname + ", id=" + id + ", reply_content=" + reply_content + ", reply_ceg_Date="
            + reply_ceg_Date + "]";
   }

   
   
   
   
}