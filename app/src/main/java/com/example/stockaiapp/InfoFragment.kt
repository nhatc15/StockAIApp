package com.example.stockaiapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stockaiapp.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvWhatIsStockContent.text = WHAT_IS_STOCK
            tvPrincipleOfOperationContent.text = PRINCIPLE_OF_OPERATION
            tvUsageModelContent.text = USSAGE_MODEL
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val WHAT_IS_STOCK =
            "Chứng khoán là một loại tài sản gồm cổ phiếu, chứng chỉ quỹ, chứng khoán phái sinh, trái phiếu, chứng quyền có bảo đảm...\n" +
                    "\n" +
                    "Theo định nghĩa trong Luật Chứng khoán, chứng khoán là tài sản bao gồm cổ phiếu, chứng chỉ quỹ, chứng khoán phái sinh, trái phiếu, chứng quyền, chứng quyền có bảo đảm, quyền mua cổ phần, chứng chỉ lưu ký. Những loại tài sản này có điểm chung là một bằng chứng xác nhận sở hữu hợp pháp của người sở hữu (gọi chung là nhà đầu tư) với tài sản của doanh nghiệp hoặc tổ chức phát hành."
        const val PRINCIPLE_OF_OPERATION =
            "- Nguyên tắc trung gian: Mọi hoạt động giao dịch, mua bán chứng khoán trên thị truờng chứng khoán đều đuợc thực hiện thông qua các trung gian, hay còn gọi là các nhà môi giới. Các nhà môi giới thực hiện giao dịch theo lệnh của khách hàng và huởng hoa hồng. Ngoài ra, nhà môi giới còn có thể cung cấp các dịch vụ khác như cung cấp thông tin và tu vấn cho khách hàng trong việc đầu tư...\n" +
                    "\n" +
                    "- Nguyên tắc đấu giá: Giá chứng khoán đuợc xác định thông qua việc đấu giá giữa các lệnh mua và các lệnh bán. Tất cả các thành viên tham gia thị truờng đều không thể can thiệp vào việc xác định giá này. Có hai hình thức đấu giá là đấu giá trực tiếp và đấu giá tự động./\n" +
                    "\n" +
                    "- Nguyên tắc công khai: Tất cả các hoạt động trên thị truờng chứng khoán đều phải đảm bảo tính công khai. Sở giao dịch chứng khoán công bố các thông tin về giao dịch chứng khoán trên thị trường. Các tổ chức niêm yết công bố công khai các thông tin tài chính định kỳ hàng năm của công ty, các sự kiện bất thuờng xảy ra đối với công ty, nắm giữ cổ phiếu của giám đốc, nguời quản lý, cổ đông đa số. Các thông tin càng được công bố công khai minh bạch, thì càng thu hút đuợc nhà đầu tư tham gia vào thị trường chứng khoán."
        const val USSAGE_MODEL =
            "Mô hình sử dụng chứng khoán là một khung tư duy hoặc công cụ tính toán được sử dụng để dự đoán giá chứng khoán hoặc hiệu suất của thị trường chứng khoán. Có nhiều mô hình khác nhau được phát triển và sử dụng trong lĩnh vực tài chính và đầu tư để giúp nhà đầu tư đưa ra quyết định thông minh về việc mua, bán hoặc giao dịch chứng khoán.\n" +
                    "\n" +
                    "Dưới đây là một số mô hình phổ biến được sử dụng trong phân tích chứng khoán:\n" +
                    "\n" +
                    "- Mô hình hồi quy tuyến tính: Mô hình này giả định rằng giá chứng khoán phụ thuộc tuyến tính vào một số yếu tố như thông tin tài chính, chỉ số kinh tế, hoặc dữ liệu lịch sử. Mô hình hồi quy tuyến tính cố gắng tìm ra một phương trình tuyến tính để dự đoán giá chứng khoán.\n" +
                    "\n" +
                    "- Mạng nơ-ron nhân tạo (Artificial Neural Networks - ANN): ANN là một mô hình tính toán được lấy cảm hứng từ cấu trúc của hệ thống thần kinh trong não người. Mạng nơ-ron nhân tạo có thể học từ dữ liệu lịch sử để tìm ra các mối quan hệ phức tạp trong dữ liệu và dự đoán giá chứng khoán trong tương lai.\n" +
                    "\n" +
                    "- Mạng nơ-ron hồi quy (Recurrent Neural Networks - RNN): RNN là một loại ANN đặc biệt được sử dụng trong việc mô hình chuỗi dữ liệu thời gian như giá chứng khoán. RNN có khả năng lưu trữ thông tin từ quá khứ và sử dụng nó để dự đoán kết quả trong tương lai.\n" +
                    "\n" +
                    "- Máy Vector Hỗ trợ (Support Vector Machines - SVM): SVM là một mô hình học máy có khả năng phân loại và dự đoán. Nó có thể được áp dụng để dự đoán hướng đi của giá cổ phiếu hoặc xác định các điểm hỗ trợ và điểm kháng cự trên biểu đồ giá chứng khoán.\n" +
                    "\n" +
                    "- Mạng nơ-ron học sâu (Deep Learning Neural Networks): Mạng nơ-ron học sâu là một dạng phức tạp của ANN với nhiều lớp ẩn. Nó có khả năng học và phân tích dữ liệu phi cấu trúc như văn bản, hình ảnh hoặc âm thanh. Trong lĩnh vực chứng khoán, mạng nơ-ron học sâu có thể được sử dụng để dự đoán xu hướng chung của thị trường hoặc xác định tín hiệu giao dịch."
    }
}

