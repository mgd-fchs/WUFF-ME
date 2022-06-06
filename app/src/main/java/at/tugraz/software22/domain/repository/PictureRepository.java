package at.tugraz.software22.domain.repository;

import androidx.lifecycle.MutableLiveData;

public interface PictureRepository {

    MutableLiveData<byte[]> downloadPicture(String path);
}
