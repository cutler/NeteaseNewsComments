package com.cutler.neteasenewscomments.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cutler.neteasenewscomments.R;
import com.cutler.neteasenewscomments.model.Comments;
import com.cutler.neteasenewscomments.model.Topic;
import com.cutler.neteasenewscomments.view.FloorListView;

public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int TYPE_TOPIC_BODY = 999;
    static final int TYPE_TOPIC_COMMENTS = 666;

    private Topic topic;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TOPIC_BODY:
                return new HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_body, parent, false));
            case TYPE_TOPIC_COMMENTS:
                return new CommentsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_comments, parent, false));
        }
        return null;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_TOPIC_BODY:
                HeaderHolder headerHolder = ((HeaderHolder) holder);
                headerHolder.title.setText(topic.getTitle());
                headerHolder.content.setText(topic.getContent());
                headerHolder.praise.setText("赞 " + topic.getPraise());
                headerHolder.tread.setText("踩 " + topic.getTread() + "");
                headerHolder.reply.setText("回复 " + topic.getReply() + "");
                headerHolder.share.setText("分享 " + topic.getShare());
                break;
            case TYPE_TOPIC_COMMENTS:
                CommentsHolder commentsHolder = ((CommentsHolder) holder);
                Comments comments = getItemByPosition(position);
                commentsHolder.commentsView.setVisibility(comments.getFloorList().size() == 0 ? View.GONE : View.VISIBLE);
                commentsHolder.username.setText(comments.getUserName());
                commentsHolder.content.setText(comments.getContent());
                commentsHolder.commentsView.setFloorList(comments.getFloorList());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return topic == null ? 0 : topic.getCommentsList().size() + 1;
    }

    public int getItemViewType(int position) {
        return position == 0 ? TYPE_TOPIC_BODY : TYPE_TOPIC_COMMENTS;
    }

    public Comments getItemByPosition(int position) {
        Comments comments = null;
        position -= 1;
        if (topic != null && position < topic.getCommentsList().size()) {
            comments = topic.getCommentsList().get(position);
        }
        return comments;
    }

    private static class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView content;
        public TextView praise;
        public TextView tread;
        public TextView reply;
        public TextView share;

        public HeaderHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            praise = (TextView) itemView.findViewById(R.id.praise);
            tread = (TextView) itemView.findViewById(R.id.tread);
            reply = (TextView) itemView.findViewById(R.id.reply);
            share = (TextView) itemView.findViewById(R.id.share);
        }
    }

    private static class CommentsHolder extends RecyclerView.ViewHolder {
        public FloorListView commentsView;
        public TextView username;
        public TextView content;

        public CommentsHolder(View itemView) {
            super(itemView);
            commentsView = (FloorListView) itemView.findViewById(R.id.commentsView);
            username = (TextView) itemView.findViewById(R.id.username);
            content = (TextView) itemView.findViewById(R.id.content);
            commentsView.setOnShowHideButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    commentsView.postDelayed(new Runnable() {
                        public void run() {
                            commentsView.appendFloorList(Comments.parseJsonForFloorList(MainActivity.TEST_JSON_2));
                        }
                    }, 800);
                }
            });
        }
    }
}