package de.otto.teamdojo.service.impl;

import de.otto.teamdojo.service.ImageService;
import de.otto.teamdojo.domain.Image;
import de.otto.teamdojo.repository.ImageRepository;
import de.otto.teamdojo.service.dto.ImageDTO;
import de.otto.teamdojo.service.mapper.ImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Image.
 */
@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    public ImageServiceImpl(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    /**
     * Save a image.
     *
     * @param imageDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ImageDTO save(ImageDTO imageDTO) {
        log.debug("Request to save Image : {}", imageDTO);

        BufferedImage img = createImageFromBytes(imageDTO.getLarge());
        BufferedImage large = resize(img, 1.0);
        BufferedImage medium = resize(img,  0.5);
        BufferedImage small = resize(img, 0.25);
        imageDTO.setLarge(getByteArrayFromBufferedImage(large));
        imageDTO.setLargeContentType("image/png");
        imageDTO.setMedium(getByteArrayFromBufferedImage(medium));
        imageDTO.setMediumContentType("image/png");
        imageDTO.setSmall(getByteArrayFromBufferedImage(small));
        imageDTO.setSmallContentType("image/png");

        Image image = imageMapper.toEntity(imageDTO);
        image = imageRepository.save(image);
        return imageMapper.toDto(image);
    }

    private byte[] getByteArrayFromBufferedImage(BufferedImage img) {
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private BufferedImage resize(BufferedImage img, double ratio) {
        int width = (int) (img.getWidth() * ratio);
        int height = (int) (img.getHeight() * ratio);
        java.awt.Image tmp = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private BufferedImage createImageFromBytes(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get all the images.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ImageDTO> findAll() {
        log.debug("Request to get all Images");
        return imageRepository.findAll().stream()
            .map(imageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one image by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ImageDTO> findOne(Long id) {
        log.debug("Request to get Image : {}", id);
        return imageRepository.findById(id)
            .map(imageMapper::toDto);
    }

    /**
     * Delete the image by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Image : {}", id);
        imageRepository.deleteById(id);
    }
}
