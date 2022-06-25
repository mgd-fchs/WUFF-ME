package at.tugraz.software22.domain.service;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import at.tugraz.software22.domain.repository.PictureRepository;

public class PictureService implements PictureRepository {
    private FirebaseStorage firebaseStorage;

    public PictureService(FirebaseStorage firebaseStorage) {
        this.firebaseStorage = firebaseStorage;
    }

    @Override
    public void downloadPicture(String path, MutableLiveData<byte[]> pictureLiveData) {
        StorageReference pictureReference = firebaseStorage.getReference().child(path);
        final long FOUR_MEGABYTE = 2048 * 2048;
        pictureReference.getBytes(FOUR_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                pictureLiveData.postValue(bytes);
                System.out.println("success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //TODO default picture
//                pictureLiveData.postValue();
                System.out.println("failure0");
                // Handle any errors
            }
        });
    }
}
