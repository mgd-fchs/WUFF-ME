package at.tugraz.software22.domain.service;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PictureServiceTest {

    @Mock
    private FirebaseStorage firebaseStorage;

    private PictureService pictureService;

    @Before
    public void setUp() {
        pictureService = new PictureService(firebaseStorage);
    }


    @Test
    public void givenPath_whenDownloadPicture_thenGetBytesIsCalled() {
        String path = "path/to/pic";
        MutableLiveData<byte[]> liveData = new MutableLiveData<>();
        StorageReference storageReference = Mockito.mock(StorageReference.class);
        StorageReference childReference = Mockito.mock(StorageReference.class);
        Task<byte[]> task = Mockito.mock(Task.class);
        Mockito.when(task.addOnSuccessListener(Mockito.any())).thenReturn(task);
        Mockito.when(childReference.getBytes(Mockito.anyLong())).thenReturn(task);
        Mockito.when(storageReference.child(path)).thenReturn(childReference);
        Mockito.when(firebaseStorage.getReference()).thenReturn(storageReference);

        pictureService.downloadPicture(path, liveData);

        Mockito.verify(childReference).getBytes(Mockito.anyLong());
    }
}
