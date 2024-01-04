package io.zenwave360.example.restaurants.adapters.web;

import io.zenwave360.example.restaurants.adapters.web.mappers.*;
import io.zenwave360.example.restaurants.adapters.web.model.*;
import io.zenwave360.example.restaurants.core.domain.*;
import io.zenwave360.example.restaurants.core.inbound.*;
import io.zenwave360.example.restaurants.core.inbound.dtos.*;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

/** REST controller for Api. */
@RestController
@RequestMapping("/api")
public class RestaurantBackOfficeApiController implements RestaurantBackOfficeApi {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired private NativeWebRequest request;

  private RestaurantBackOfficeService restaurantBackOfficeService;

  @Autowired
  public RestaurantBackOfficeApiController setRestaurantBackOfficeService(RestaurantBackOfficeService restaurantBackOfficeService) {
    this.restaurantBackOfficeService = restaurantBackOfficeService;
    return this;
  }

  private RestaurantBackOfficeDTOsMapper mapper = RestaurantBackOfficeDTOsMapper.INSTANCE;

  @Override
  public Optional<NativeWebRequest> getRequest() {
    return Optional.ofNullable(request);
  }

  @Override
  public ResponseEntity<RestaurantDTO> createRestaurant(RestaurantDTO reqBody) {
    Restaurant input = mapper.asRestaurant(reqBody);
    Restaurant restaurant = restaurantBackOfficeService.createRestaurant(input);
    RestaurantDTO responseDTO = mapper.asRestaurantDTO(restaurant);
    return ResponseEntity.status(201).body(responseDTO);
  }

  @Override
  public ResponseEntity<RestaurantDTO> getRestaurant(String restaurantId) {
    Optional<Restaurant> optionalRestaurant = restaurantBackOfficeService.getRestaurant(restaurantId);
    if (optionalRestaurant.isPresent()) {
      RestaurantDTO responseDTO = mapper.asRestaurantDTO(optionalRestaurant.get());
      return ResponseEntity.status(200).body(responseDTO);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Override
  public ResponseEntity<RestaurantPaginatedDTO> listRestaurants(Optional<Integer> page, Optional<Integer> limit, Optional<List<String>> sort) {
    Page<Restaurant> pageRestaurant = restaurantBackOfficeService.listRestaurants(pageOf(page, limit, sort));
    RestaurantPaginatedDTO responseDTO = mapper.asRestaurantPaginatedDTO(pageRestaurant);
    return ResponseEntity.status(200).body(responseDTO);
  }

  @Override
  public ResponseEntity<MenuItemDTO> createMenuItem(String restaurantId, MenuItemDTO reqBody) {
    MenuItem input = mapper.asMenuItem(reqBody);
    MenuItem menuItem = restaurantBackOfficeService.createMenuItem(input);
    MenuItemDTO responseDTO = mapper.asMenuItemDTO(menuItem);
    return ResponseEntity.status(201).body(responseDTO);
  }

  @Override
  public ResponseEntity<List<MenuItemDTO>> listMenuItems(String restaurantId) {
    List<MenuItem> listMenuItem = restaurantBackOfficeService.listMenuItems(restaurantId);
    List<MenuItemDTO> responseDTO = mapper.asMenuItemListDTOList(listMenuItem);
    return ResponseEntity.status(200).body(responseDTO);
  }

  @Override
  public ResponseEntity<MenuItemDTO> updateMenuItem(String restaurantId, String name, MenuItemDTO reqBody) {
    MenuItem input = mapper.asMenuItem(reqBody);
    Optional<MenuItem> optionalMenuItem = restaurantBackOfficeService.updateMenuItem(restaurantId, input);
    if (optionalMenuItem.isPresent()) {
      MenuItemDTO responseDTO = mapper.asMenuItemDTO(optionalMenuItem.get());
      return ResponseEntity.status(200).body(responseDTO);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  protected Pageable pageOf(Optional<Integer> page, Optional<Integer> limit, Optional<List<String>> sort) {
    return PageRequest.of(page.orElse(0), limit.orElse(10));
  }
}
