//package com.darkshadows.shady.studentsapp;
//
//import android.content.Context;
//import android.net.Uri;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//
//import java.text.DateFormat;
//import java.util.List;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class CommentsRecyclerAdapter extends RecyclerView.Adapter<CommentsRecyclerAdapter.ViewHolder> {
//
//    public List<Comments> commentsList;
//    public Context context;
//    public FirebaseFirestore firebaseFirestore;
//    public FirebaseAuth mAuth;
//
//    public CommentsRecyclerAdapter(List<Comments> commentsList){
//
//        this.commentsList = commentsList;
//
//    }
//
//    @Override
//    public CommentsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
//        context = parent.getContext();
//        mAuth = FirebaseAuth.getInstance();
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        return new CommentsRecyclerAdapter.ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final CommentsRecyclerAdapter.ViewHolder holder, int position) {
//
//        holder.setIsRecyclable(false);
//
//        String currentUserId = mAuth.getCurrentUser().getUid();
//        String image_url = commentsList.get(position).getImage_url();
//
//        String commentMessage = commentsList.get(position).getMessage();
//        holder.setComment_message(commentMessage);
//
//        String user_id = commentsList.get(position).getUser_id();
//        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful())
//                {
//                    String userName = task.getResult().getString("name");
//                    String userImage = task.getResult().getString("image");
//
//                    holder.setUsername(userName);
//                    holder.setCommentImage(userImage);
//                }
//                else
//                {
//                    //Firebase Exceptions
//                }
//            }
//        });
//
//        try {
//            long milliseconds = commentsList.get(position).getTimestamp().getTime();
//            String dateString = DateFormat.getDateTimeInstance().format(milliseconds);
//            holder.setTime(dateString);
//        }
//        catch (Exception e)
//        {
//        }
//
//    }
//
//
//    @Override
//    public int getItemCount() {
//
//        if(commentsList != null) {
//
//            return commentsList.size();
//
//        } else {
//
//            return 0;
//
//        }
//
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        private View mView;
//
//        private TextView comment_message;
//        private TextView comment_username;
//        private TextView comment_date;
//        private CircleImageView comment_image;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            mView = itemView;
//        }
//
//        public void setComment_message(String message) {
//
//            comment_message = mView.findViewById(R.id.comment_message);
//            comment_message.setText(message);
//        }
//
//        public void setUsername(String username)
//        {
//            comment_username = mView.findViewById(R.id.comment_username);
//            comment_username.setText(username);
//        }
//
//        public void setCommentImage(String ImgUri)
//        {
//            comment_image = mView.findViewById(R.id.comment_image);
//            RequestOptions placeholderOption = new RequestOptions();
//            placeholderOption.placeholder(R.drawable.display_picture_placeholder);
//
//            Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(ImgUri).into(comment_image);
//
//        }
//
//        public void setTime(String date)
//        {
//            comment_date = mView.findViewById(R.id.comment_date);
//            comment_date.setText(date);
//        }
//
//    }
//}
