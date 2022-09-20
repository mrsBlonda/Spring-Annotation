package ru.netology.repository;

import org.springframework.stereotype.Component;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class PostRepository {
  final ConcurrentHashMap<Long, String> map = new ConcurrentHashMap<>();
  final AtomicLong key = new AtomicLong(1);
  public List<Post> all() {
    List<Post> posts = new ArrayList<>();
    Post post;
    for (var set : map.entrySet()) {
      post = new Post(set.getKey(), set.getValue());
      posts.add(post);
    }
    return posts;
  }

  public Optional<Post> getById(long id) {
    Post post = null;
    if (map.containsKey(id)) {
      post = new Post(id, map.get(id));
    }
    final var result = Optional.ofNullable(post);
    return result;
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      while (map.containsKey(key)) {
        key++;
      }
      map.put(key, post.getContent());
      key++;
    } else {
      map.put(post.getId(), post.getContent());
    }
    return post;
  }

  public void removeById(long id) {
    if (map.containsKey(id)) {
      map.remove(id);
    }
  }
}
