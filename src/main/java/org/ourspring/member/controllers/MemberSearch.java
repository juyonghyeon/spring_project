package org.ourspring.member.controllers;

import lombok.Data;
import org.ourspring.global.search.CommonSearch;
import org.ourspring.member.constants.Authority;

import java.util.List;

@Data
public class MemberSearch extends CommonSearch {
    private List<Authority> authority;
}
