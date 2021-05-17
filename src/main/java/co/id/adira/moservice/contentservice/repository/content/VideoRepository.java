package co.id.adira.moservice.contentservice.repository.content;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.id.adira.moservice.contentservice.model.content.PilihanLain;
import co.id.adira.moservice.contentservice.model.content.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
	List<Video> findTop8ByActiveOrderByIdDesc(boolean active);
}
