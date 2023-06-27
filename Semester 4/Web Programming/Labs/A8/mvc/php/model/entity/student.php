<?php

class Student implements JsonSerializable {
    private $id;
    private $firstName;
    private $lastName;
    private $group;
    private $username;
    private $password;

    public function __construct($id, $firstName, $lastName, $group, $username, $password) {
        $this->id = $id;
        $this->firstName = $firstName;
        $this->lastName = $lastName;
        $this->group = $group;
        $this->username = $username;
        $this->password = $password;
    }

    //public function __construct(){}

    public function getId()
    {
        return $this->id;
    }
    public function setId($id)
    {
        $this->id = $id;
    }
    public function getFirstName()
    {
        return $this->firstName;
    }
    public function setFirstName($firstName)
    {
        $this->firstName = $firstName;
    }
    public function getLastName()
    {
        return $this->lastName;
    }
    public function setLastName($lastName)
    {
        $this->lastName = $lastName;
    }
    public function getGroup()
    {
        return $this->group;
    }
    public function setGroup($group)
    {
        $this->group = $group;
    }
    public function getUsername()
    {
        return $this->username;
    }
    public function setUsername($username)
    {
        $this->username = $username;
    }
    public function getPassword()
    {
        return $this->password;
    }
    public function setPassword($password)
    {
        $this->password = $password;
    }

    public function jsonSerialize() {
        $vars = get_object_vars($this);
        return $vars;
    }
}

?>
