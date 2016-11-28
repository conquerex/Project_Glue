package com.hm.project_glue.main;



public interface MainPresenter {

    void setView(MainPresenter.View view);



    public interface View {
        public void setButtonText(String text);
    }
}
