package at.tugraz.software22.domain.repository;

import androidx.lifecycle.MutableLiveData;

public interface PictureRepository {

    void downloadPicture(String path, MutableLiveData<byte[]> pictureLiveData);
}
