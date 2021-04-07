package my.insta.MeetPetV6.models;

public class Pet {
    private String sex, petClass, type, age, name, description, ownerID, image_Path, pet_id;

    public Pet() {
    }

    public Pet(String sex, String petClass, String type, String age, String name, String description, String ownerID, String image_Path, String pet_id) {
        this.sex = sex;
        this.petClass = petClass;
        this.type = type;
        this.age = age;
        this.name = name;
        this.description = description;
        this.ownerID = ownerID;
        this.image_Path = image_Path;
        this.pet_id = pet_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPetClass() {
        return petClass;
    }

    public void setPetClass(String petClass) {
        this.petClass = petClass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getImage_Path() {
        return image_Path;
    }

    public void setImage_Path(String image_Path) {
        this.image_Path = image_Path;
    }

    public String getPet_id() {
        return pet_id;
    }

    public void setPet_id(String pet_id) {
        this.pet_id = pet_id;
    }
}