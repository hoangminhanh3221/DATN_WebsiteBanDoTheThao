package com.shop.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shop.entity.Brand;
import com.shop.entity.Color;
import com.shop.entity.Discount;
import com.shop.entity.Image;
import com.shop.entity.Product;
import com.shop.entity.Size;
import com.shop.entity.Subcategory;
import com.shop.service.BrandService;
import com.shop.service.ColorService;
import com.shop.service.DiscountService;
import com.shop.service.ImageService;
import com.shop.util.ImageUtil;
import com.shop.service.ProductService;
import com.shop.service.SizeService;
import com.shop.service.SubcategoryService;

@Controller
public class AdminProductController {

	@Autowired
	ProductService productService;
	@Autowired
	SubcategoryService subcategoryService;
	@Autowired
	BrandService brandService;
	@Autowired
	ColorService colorService;
	@Autowired
	SizeService sizeService;
	@Autowired
	ImageUtil imageUtil;

	@Autowired
	ImageService imageService;
	@Autowired
	DiscountService discountService;
	// String uploadDir =
	// "C:/Users/Dung/Documents/GitHub/DoAnTotNghiep/DATN_ShopTheThao/src/main/resources/static/user-page/images/product/";

	String uploadDir = "C:/Users/hma20/OneDrive/Documents/GitHub/DoAnTotNghiep/DATN_ShopTheThao/src/main/resources/static/user-page/images/products/";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@GetMapping("/admin/product")
	public String ProductList(
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(value = "status", required = false) Integer status, Model model) {

		int pageSize = 10; // Số phần tử trên mỗi trang
		Pageable pageable = PageRequest.of(page, pageSize);
		List<Product> list = new ArrayList<>();

		if (status == null) {
			status = 0;
		}
		Page<Product> pageProduct;
		if (status != 0) {
			pageProduct = productService.getProductsByStatusDel(pageable, Boolean.TRUE);

		} else {
			pageProduct = productService.getProductsByStatusDel(pageable, Boolean.FALSE);
		}
		model.addAttribute("list", list);
		model.addAttribute("status", status);
		model.addAttribute("page", pageProduct);
		System.out.println(pageProduct.getNumber());
		return "admin-page/product-list";
	}

	@GetMapping("/admin/product/add")
	public String ProductAdd(Model model) {

		Product pro = new Product();

		List<Size> SizeL = sizeService.findAllSize();
		List<Color> ColorL = colorService.findAllColor();
		List<Brand> BrandL = brandService.findAllBrand();
		List<Discount> DiscountL = discountService.findAllDiscount();
		List<Subcategory> SubcategoryL = subcategoryService.findAllSubcategory();

		model.addAttribute("subcategoryL", SubcategoryL);
		model.addAttribute("sizeL", SizeL);
		model.addAttribute("colorL", ColorL);
		model.addAttribute("discountL", DiscountL);
		model.addAttribute("brandL", BrandL);
		model.addAttribute("product", pro);
		model.addAttribute("status", 1);
		return "admin-page/product-add";
	}

	@GetMapping("/admin/product/restore")
	public String RestoreProduct(Model model, @RequestParam("id") String id) {

		Product prd = productService.findProductById(id).get();
		prd.setIsDeleted(false);
		productService.updateProduct(prd);
		return "redirect:/admin/product?status=1";
	}

	@PostMapping("/admin/product/add/save")
	public String ProductAddSave(Model model, @ModelAttribute("product") Product product,
			@RequestParam("image1") MultipartFile image1, @RequestParam("image2") MultipartFile image2,
			@RequestParam("image3") MultipartFile image3, @RequestParam("image4") MultipartFile image4)
			throws IOException {

		Image img = new Image();

		String new1 = UUID.randomUUID().toString();
		String new2 = UUID.randomUUID().toString();
		String new3 = UUID.randomUUID().toString();
		String new4 = UUID.randomUUID().toString();

		img.setImageName1(imageUtil.saveImage(image1, new1, "products"));
		img.setImageName2(imageUtil.saveImage(image2, new2, "products"));
		img.setImageName3(imageUtil.saveImage(image3, new3, "products"));
		img.setImageName4(imageUtil.saveImage(image4, new4, "products"));

		img.setImageId(String.valueOf(generateRandomSevenDigitNumber()));
		product.setImage(img);
		product.setIsDeleted(false);
		product.setQuantityLeft(product.getTotalQuantity());
		imageService.createImage(img);
		productService.createProduct(product);

		return "redirect:/admin/product";
	}

	// ------------------------------------------------------------

	@GetMapping("/admin/product/update")
	public String ProductUpdate(Model model, @RequestParam("id") String id) {

		Product pro = productService.findProductById(id).get();

		List<Size> SizeL = sizeService.findAllSize();
		List<Color> ColorL = colorService.findAllColor();
		List<Brand> BrandL = brandService.findAllBrand();
		List<Discount> DiscountL = discountService.findAllDiscount();
		List<Subcategory> SubcategoryL = subcategoryService.findAllSubcategory();

		model.addAttribute("image1", pro.getImage().getImageName1());
		model.addAttribute("image2", pro.getImage().getImageName2());
		model.addAttribute("image3", pro.getImage().getImageName3());
		model.addAttribute("image4", pro.getImage().getImageName4());
		model.addAttribute("subcategoryL", SubcategoryL);
		model.addAttribute("sizeL", SizeL);
		model.addAttribute("colorL", ColorL);
		model.addAttribute("discountL", DiscountL);
		model.addAttribute("brandL", BrandL);
		model.addAttribute("product", pro);
		model.addAttribute("status", 0);
		return "admin-page/product-add";
	}

	@PostMapping("/admin/product/update/save")
	public String ProductUpdateSave(Model model, @ModelAttribute("product") Product product,
			@RequestParam(value = "image1", required = false) MultipartFile image1,
			@RequestParam(value = "image2", required = false) MultipartFile image2,
			@RequestParam(value = "image3", required = false) MultipartFile image3,
			@RequestParam(value = "image4", required = false) MultipartFile image4) throws IOException {

		Product pro = productService.findProductById(product.getProductId()).get();
		Image img = pro.getImage();

		if (image1 != null && !image1.isEmpty()) {

			String filename1 = image1.getOriginalFilename();
			String filename2 = image2.getOriginalFilename();
			String filename3 = image3.getOriginalFilename();
			String filename4 = image4.getOriginalFilename();

			String new1 = UUID.randomUUID().toString();
			String new2 = UUID.randomUUID().toString();
			String new3 = UUID.randomUUID().toString();
			String new4 = UUID.randomUUID().toString();

			img.setImageName1(imageUtil.saveImage(image1, new1, "products"));
			img.setImageName2(imageUtil.saveImage(image2, new2, "products"));
			img.setImageName3(imageUtil.saveImage(image3, new3, "products"));
			img.setImageName4(imageUtil.saveImage(image4, new4, "products"));
		}
		product.setIsDeleted(false);
		product.setImage(img);
		imageService.updateImage(img);
		productService.updateProduct(product);
		return "redirect:/admin/product";
	}

	@GetMapping("/admin/product/delete")
	public String ProductDelete(Model model, @RequestParam("id") String id) {

		Product product = productService.findProductById(id).get();
		product.setIsDeleted(true);
		productService.updateProduct(product);

		return "redirect:/admin/product";
	}

	// -----function
	public static int generateRandomSevenDigitNumber() {
		Random random = new Random();
		return 1000000 + random.nextInt(9000000); // Tạo số trong khoảng từ 1000000 đến 9999999
	}
}