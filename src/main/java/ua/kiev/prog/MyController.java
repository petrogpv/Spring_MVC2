package ua.kiev.prog;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MyController {

    private List<Photo> photos = new ArrayList<>();

    @RequestMapping("/")
    public String onIndex() {
        return "index";
    }

    @RequestMapping(value = "/add_photo", method = RequestMethod.POST)
    public String onAddPhoto(Model model, @RequestParam MultipartFile photo) {
        if (photo.isEmpty())
            throw new PhotoErrorException();

        try {
            long id = System.currentTimeMillis();
            photos.add(new Photo(id, photo.getBytes()));

            model.addAttribute("photo_id", id);
            return "result";
        } catch (IOException e) {
            throw new PhotoErrorException();
        }
    }

    @RequestMapping("/photo/{photo_id}")
    public ResponseEntity<byte[]> onPhoto(@PathVariable("photo_id") long id) {
        return photoById(id);
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseEntity<byte[]> onView(@RequestParam("photo_id") long id) {
        return photoById(id);
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String onList(Model model) {
        model.addAttribute("photos",photos);
        return "list";
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity onDel(@RequestParam(value = "toDelete[]", required = false) long[] deletes,  Model model) throws IOException, URISyntaxException {
        HttpHeaders headersResponse = new HttpHeaders();
        headersResponse.setLocation(new URI("/list"));
        if (deletes==null)
            return new ResponseEntity(headersResponse, HttpStatus.FOUND);

        for (int j = 0; j<deletes.length;j++) {
            for (int i = 0; i < photos.size(); i++) {
                if (photos.get(i).getId() == deletes[j]) {
                    photos.remove(i);
                    break;
                }
            }
        }
        return new ResponseEntity(headersResponse, HttpStatus.FOUND);
    }

    @RequestMapping("/delete/{photo_id}")
    public String onDelete(@PathVariable("photo_id") long id) {
        int chk = 0;
        for (int i =0; i <photos.size();i++) {
            if(photos.get(i).getId()==id) {
                photos.remove(i);
                chk++;
            }
        }
        if (chk == 0)
            throw new PhotoNotFoundException();
        else
            return "index";
    }

    private ResponseEntity<byte[]> photoById(long id) {

        byte[] bytes = null;
        for (Photo ph: photos) {
            if(ph.getId()==id) {
                bytes = ph.getData();
            }
        }
        if (bytes == null)
            throw new PhotoNotFoundException();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }
}
