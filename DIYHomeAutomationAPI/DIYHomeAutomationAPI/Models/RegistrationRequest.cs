using System.ComponentModel.DataAnnotations;
using System.Data;

namespace DIYHomeAutomationAPI.Models
{
    public class RegistrationRequest
    {
        [Required]
        public string Name { get; set; } = null!;

        [Required]
        public string Email { get; set; } = null!;

        [Required]
        public string UserName { get; set; } = null!;

        [Required]
        public string Password { get; set; } = null!;
    }
}
