package at.tugraz.software22.domain.service;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import at.tugraz.software22.domain.repository.PictureRepository;

public class PictureService implements PictureRepository {
    private final FirebaseStorage firebaseStorage;

    public PictureService(FirebaseStorage firebaseStorage) {
        this.firebaseStorage = firebaseStorage;
    }

    @Override
    public void downloadPicture(String path, MutableLiveData<byte[]> pictureLiveData) {
        StorageReference pictureReference = firebaseStorage.getReference().child(path);
        final long FOUR_MEGABYTE = 2048 * 2048;
        pictureReference.getBytes(FOUR_MEGABYTE).addOnSuccessListener(pictureLiveData::postValue);
    }
}
