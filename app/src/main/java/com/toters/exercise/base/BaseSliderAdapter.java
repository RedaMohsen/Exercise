package com.toters.exercise.base;



public class BaseSliderAdapter /*extends SliderAdapter*/ {
/*    private ArrayList<Image> items = new ArrayList<>();

    public BaseSliderAdapter() {
        items.add(new Image());
    }

    public BaseSliderAdapter(ArrayList<Image> images) {
        items.clear();
        this.items = images;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        imageSlideViewHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Image image = items.get(position);
        if (image.getUrl() != null) {
            ImageUtils.loadImage(BuildConfig.BASE_URL + image.getUrl(), imageSlideViewHolder.imageView, R.drawable.img_home);
        } else {
            imageSlideViewHolder.imageView.setImageResource(image.getDefaultValue());
        }
    }*/
}
