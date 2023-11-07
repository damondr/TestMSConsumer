package org.damon.st.consumer.controller;

import lombok.RequiredArgsConstructor;
import org.damon.st.consumer.dto.UserDto;
import org.damon.st.consumer.model.User;
import org.damon.st.consumer.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<UserDto> searchUsers(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "surname", required = false) String surname) {
        List<User> users;

        if (name != null && surname != null) {
            users = usersService.searchByNameAndSurname(name,surname);
        } else if (name != null) {
            users = usersService.searchByName(name);
        } else if (surname != null) {
            users = usersService.searchBySurname(surname);
        } else {
            users = usersService.findAll();
        }

        return convertToDtoList(users);
    }

    private List<UserDto> convertToDtoList(List<User> users) {
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }
}